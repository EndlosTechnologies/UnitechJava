package com.unitechApi.MachineSetParameter.DtoRest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimplexRest {
    private Long id;
    private float shift_b_sixHoursOne;
    private float avervg_difference_b_sixHoursOne;
    private float shift_b_sixHoursTwo;
    private float avervg_difference_b_sixHoursTwo;
    private float total_shift_prod_b;

    private float shift_a_sixHoursOne;
    private float avervg_difference_a_sixHoursOne;
    private float shift_a_sixHoursTwo;
    private float avervg_difference_a_sixHoursTwo;
    private float total_shift_prod_a;


    private float actual_producation;
    private float efficiency;
    private float target_prod_variance_kg;
    private float target_prod_variance;
}
