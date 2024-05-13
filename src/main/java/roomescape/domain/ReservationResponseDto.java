package roomescape.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReservationResponseDto {

    public Long id;
    public String name;
    public String date;
    public String time;

    public static ReservationResponseDto from(Reservation reservation) {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setId(reservation.getId());
        dto.setName(reservation.getName());
        dto.setDate(reservation.getDateTime().toLocalDate().toString());
        dto.setTime(reservation.getDateTime().toLocalTime().toString());
        return dto;
    }
}
