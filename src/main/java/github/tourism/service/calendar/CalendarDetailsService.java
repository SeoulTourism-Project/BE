package github.tourism.service.calendar;

import github.tourism.data.entity.calendar.CalendarDetails;
import github.tourism.data.repository.calendar.CalendarDetailsRepository;
import github.tourism.data.repository.calendar.CalendarRepository;
import github.tourism.web.advice.ErrorCode;
import github.tourism.web.dto.calendar.CalendarDetailsDTO;
import github.tourism.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static github.tourism.web.advice.ErrorCode.USER_NOT_FOUNDED;

@Service
@RequiredArgsConstructor
public class CalendarDetailsService {
    private final CalendarDetailsRepository calendarDetailsRepository;
    private final CalendarRepository calendarRepository;

    // 캘린더 상세 내용 가져오기 : userId & tourStartDate에 기반하여
    public List<CalendarDetailsDTO> getCalendarDetailsByUserAndDate(int userId, LocalDate tourStartDate) {
        final LocalDateTime tourAtStartOfDay = tourStartDate.atStartOfDay();
        List<CalendarDetails> calendarDetailsList = calendarDetailsRepository.findCalendarDetailsWithFavPlaceByTourStartDate(userId, tourStartDate, tourAtStartOfDay, tourAtStartOfDay.plusDays(1));
        return this.convertCalendarDetailsListToDTOList(calendarDetailsList);//calendarDetailsList.stream().map(this::convertCalendarDetailsToDTO).toList();
    }

    // 캘린더 상세 내용 가져오기 : userId 에 기반하여
    public List<CalendarDetailsDTO> getCalendarDetailsByUser(int userId) {
        List<CalendarDetails> calendarDetailsList = calendarDetailsRepository.findCalendarDetailsWithFavPlace(userId);
        return this.convertCalendarDetailsListToDTOList(calendarDetailsList);//calendarDetailsList.stream().map(this::convertCalendarDetailsToDTO).toList();
    }

    // 캘린더 상세내용 지우기 : userId & calendarId 에 기반하여
    public void deleteCalendarDetailsByUserIdAndCalendarId(int userId, int calendarId) {
        int affectedRows = calendarDetailsRepository.deleteCalendarDetailsByUserIdAndCalendarId(userId, calendarId);
        if (affectedRows == 0) {
            throw new NotFoundException(USER_NOT_FOUNDED);
        }
    }

    // 특정 사용자가 등록한 모든 여행 날짜를 반환
    public List<LocalDate> getDistinctTourStartDatesByUserId(int userId){
        return calendarRepository.findDistinctTourStartDatesByUserId(userId);
    }


    private List<CalendarDetailsDTO> convertCalendarDetailsListToDTOList(List<CalendarDetails> calendarDetailsList) {
        return calendarDetailsList.stream()
                .map(this::convertCalendarDetailsToDTO)
                .toList();
    }

    private CalendarDetailsDTO convertCalendarDetailsToDTO(CalendarDetails calendarDetails) {
        return new CalendarDetailsDTO(
                calendarDetails.getCalendarDetailsId(),
                calendarDetails.getPlaceName(),
                calendarDetails.getCalendar().getScheduleTime(),
                calendarDetails.getCalendar().getScheduleEndTime(),
                calendarDetails.getCalendar().getMemo(),
                calendarDetails.getCalendar().getFavPlace().getPlaceName(),
                calendarDetails.getCalendar().getFavPlace().getPlaceImage(),
                calendarDetails.getCalendar().getFavPlace().getPlaceLocation()
        );
    }
}


//    // 1. 특정 캘린더의 세부 정보 조회
//    public List<CalendarDetails> getCalendarDetails(int calendarId) {
//        if (!calendarDetailsRepository.existsById(calendarId)) {
//            throw new IllegalArgumentException("해당 캘린더가 존재하지 않습니다. ID: " + calendarId);
//        }
//        return calendarDetailsRepository.findAllByCalendarId(calendarId);
//    }
//
//    // 상세 정보 추가
//    public List<CalendarDetails> addCalendarDetails(List<CalendarDetails> calendarDetails) {
//        if (calendarDetails == null || calendarDetails.isEmpty()) {
//            throw new IllegalArgumentException("세부 정보가 비어 있습니다.");
//        }
//
//        // 검증 로직 추가
//        for (CalendarDetails detail : calendarDetails) {
//            if (detail.getCalendarId() == null || detail.getPlaceName() == null) {
//                throw new IllegalArgumentException("필수 세부 정보가 누락되었습니다.");
//            }
//        }
//
//        return calendarDetailsRepository.saveAll(calendarDetails);
//    }

