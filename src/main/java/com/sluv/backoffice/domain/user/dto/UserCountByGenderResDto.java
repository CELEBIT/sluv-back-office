package com.sluv.backoffice.domain.user.dto;

import static com.sluv.backoffice.domain.user.enums.UserGender.MAN;
import static com.sluv.backoffice.domain.user.enums.UserGender.UNKNOWN;
import static com.sluv.backoffice.domain.user.enums.UserGender.WOMAN;

import com.sluv.backoffice.domain.user.enums.UserGender;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCountByGenderResDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EachUserCountByGenderResDto {
        private UserGender gender;
        @Schema(description = "성별에 해당하는 유저의 수")
        private Long count;
        private Double percent;
    }

    @Schema(description = "각 성별의 통계")
    private List<EachUserCountByGenderResDto> eachGender;
    @Schema(description = "전체 유저의 수")
    private Long totalCount;

    public static UserCountByGenderResDto of(HashMap<UserGender, Long> countByGender, Long totalCount) {
        List<EachUserCountByGenderResDto> userCountByEachGenders = List.of(
                new EachUserCountByGenderResDto(MAN, countByGender.get(MAN),
                        getPercent(countByGender.get(MAN), totalCount)),
                new EachUserCountByGenderResDto(WOMAN, countByGender.get(WOMAN),
                        getPercent(countByGender.get(WOMAN), totalCount)),
                new EachUserCountByGenderResDto(UNKNOWN, countByGender.get(UNKNOWN),
                        getPercent(countByGender.get(UNKNOWN), totalCount)));
        return new UserCountByGenderResDto(userCountByEachGenders, totalCount);
    }

    private static Double getPercent(Long genderCount, Long totalCount) {
        if (totalCount == 0) {
            return 0.0;
        }

        // 계산 후 소숫점 2자리까지 반올림1
        return BigDecimal.valueOf((double) genderCount / totalCount * 100)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
