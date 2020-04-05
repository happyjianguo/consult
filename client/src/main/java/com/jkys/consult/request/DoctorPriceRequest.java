package com.jkys.consult.request;

import com.jkys.phobos.exception.ServiceError;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class DoctorPriceRequest {
    private Long doctorId;
    private Integer price;

    public DoctorPriceRequest(Long doctorId, Integer price) {
        if (ObjectUtils.isEmpty(price)) {
            throw new ServiceError("0", "医生价格不能为空");
        }
        if (price < 0 || price > 20000) {
            throw new ServiceError("0", "医生价格范围为0~20000");
        }
        this.doctorId = doctorId;
        this.price = price;
    }
}
