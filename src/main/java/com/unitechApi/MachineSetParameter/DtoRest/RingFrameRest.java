package com.unitechApi.MachineSetParameter.DtoRest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RingFrameRest {
    private Long id;
    private float shift_a_twoHoursOne;
    private float avervg_difference_a_twoHoursOne;


    private float shift_a_twoHoursTwo;
    private float averageshift_a_HankOne;
    private float avervg_difference_a_twoHoursTwo;


    private float shift_a_twoHoursThree;
    private float avervg_difference_a_twoHoursThree;


    private float shift_a_twoHoursFour;
    private float avervg_difference_a_twoHoursFour;


    private float shift_a_twoHoursFive;
    private float avervg_difference_a_twoHoursFive;


    private float shift_a_twoHoursSix;
    private float avervg_difference_a_twoHoursSix;


    private float total_shift_prod_a;



    private float shift_b_twoHoursOne;
    private float avervg_difference_b_twoHoursOne;


    private float shift_b_twoHoursTwo;
    private float avervg_difference_b_twoHoursTwo;


    private float shift_b_twoHoursThree;
    private float avervg_difference_b_twoHoursThree;


    private float shift_b_twoHoursFour;
    private float avervg_difference_b_twoHoursFour;


    private float shift_b_twoHoursFive;
    private float avervg_difference_b_twoHoursFive;


    private float shift_b_twoHoursSix;
    private float avervg_difference_b_twoHoursSix;


    private float total_shift_prod_b;


    private float actual_producation;
    private float efficiency;
    private float target_prod_variance_kg;
    private float target_prod_variance;
}
