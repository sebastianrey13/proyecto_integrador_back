package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ReservationDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.UserDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Reservation;
import com.grupo5.AlquilerEquiposConstruccion.repository.ReservationRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.EmailService;
import com.grupo5.AlquilerEquiposConstruccion.service.ProductService;
import com.grupo5.AlquilerEquiposConstruccion.service.ReservationService;
import com.grupo5.AlquilerEquiposConstruccion.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final Logger logger = Logger.getLogger(CategoryServiceImpl.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Override
    public List<ReservationDTO> findByUserId(Integer userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        for(Reservation reservation: reservations){
            LocalDate todayDate = LocalDate.now();
            LocalDate checkout = reservation.getCheckout_date();
            if(todayDate.isAfter(checkout)){
                reservation.setActive(false);
                reservationRepository.save(reservation);
            }
        }
        return reservations.stream()
                .map(reservation -> mapper.convertValue(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> findByProductId(Integer productId) {
        List<Reservation> reservations = reservationRepository.findByProductId(productId);
        return reservations.stream()
                .map(reservation -> mapper.convertValue(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getAllReservation() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(reservation -> mapper.convertValue(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationDTO> getReservationById(Integer id) throws NotFoundException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "reservation with the id: " + id + " was not found."));;
        return Optional.ofNullable(mapper.convertValue(reservation, ReservationDTO.class));
    }

    @Override
    public ReservationDTO saveReservation(ReservationDTO reservationDTO) throws NotFoundException {
        Reservation reservation = mapper.convertValue(reservationDTO, Reservation.class);
        LocalDate checkinDate = reservation.getCheck_in_date();
        LocalDate todayDate = LocalDate.now();
        if(checkinDate.isAfter(todayDate)) {
            reservation.setActive(true);
        }
        Optional<UserDTO> user = userService.getUserById(reservation.getUser().getId());
        Optional<ProductDTO> product = productService.getProductById(reservation.getProduct().getId());

        String email = user.get().getEmail();
        String productName = product.get().getName();
        String checkin = reservation.getCheck_in_date().toString();
        String checkout = reservation.getCheckout_date().toString();

        Reservation savedReservation = reservationRepository.save(reservation);
        logger.info("The reservation was created successfully.");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Reservación completa en AlquiConstruye!");
        mailMessage.setText("Se realizó un proceso de reserva para el usuario con email: "
                + email + ".\n\n"
                + "Detalles de la reserva:\n"
                + "Producto: " + productName + ".\n"
                + "Fecha de retiro: " + checkin + ".\n"
                + "Fecha de devolución: " + checkout + ".");
        emailService.sendEmail(mailMessage);

        return mapper.convertValue(savedReservation, ReservationDTO.class);
    }


    @Override
    public ReservationDTO updateReservation(ReservationDTO reservationDTO) throws NotFoundException {
        Integer id = reservationDTO.getId();
        Optional<ReservationDTO> existingReservation = getReservationById(id);
        if (existingReservation.isPresent()){
            existingReservation.get().setCheck_in_date(reservationDTO.getCheck_in_date());
            existingReservation.get().setCheckout_date(reservationDTO.getCheckout_date());
            existingReservation.get().setComments(reservationDTO.getComments());
            existingReservation.get().setUser(reservationDTO.getUser());
            existingReservation.get().setProduct(reservationDTO.getProduct());
            Reservation reservation = mapper.convertValue(reservationDTO, Reservation.class);
            reservationRepository.save(reservation);
            logger.info("The reservation was updated successfully.");
        }
        return mapper.convertValue(existingReservation, ReservationDTO.class);
    }


    @Override
    public void deleteReservationById(Integer id) {
        reservationRepository.deleteById(id);
    }
}

