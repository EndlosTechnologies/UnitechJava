package com.unitechApi.store.svendor.model;

import com.unitechApi.user.model.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store_vendor",schema = "store_management")
public class SvendorDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long svid;
    private long empid;
    private long quantity;
    private long total;
    private int price;


    

}
