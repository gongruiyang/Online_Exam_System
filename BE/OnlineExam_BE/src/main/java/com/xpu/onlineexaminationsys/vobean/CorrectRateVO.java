package com.xpu.onlineexaminationsys.vobean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CorrectRateVO {
    Integer id;
    Integer rightCount;
    Float rightRate;
}
