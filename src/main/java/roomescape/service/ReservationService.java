package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.BadRequestException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<ReservationResponse> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponse> responseDtoList = reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());

        return responseDtoList;
    }

    public ReservationResponse addReservation(ReservationRequest reservationRequest){
        String name = reservationRequest.getName();
        LocalDate date = reservationRequest.getDate();
        LocalTime time = reservationRequest.getTime();


        if (name == null || name.isEmpty() || date == null || time == null) {
            throw new BadRequestException("예약 정보가 올바르지 않습니다.");
        }

        Reservation reservation = new Reservation();
        reservation.setName(name);
        reservation.setDate(date);
        reservation.setTime(time);

        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationResponse.from(savedReservation);
    }

    public void cancelReservation(Long id) {
        ReservationResponse reservationResponse = findById(id);
        reservationRepository.deleteById(reservationResponse.getId());
    }

    public ReservationResponse findById(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        if(reservation == null) {
            throw new BadRequestException("취소할 예약을 찾을 수 없습니다. (id=" + id + ")");
        }
        return ReservationResponse.from(reservation);
    }
}