package com.unitechApi.user.training.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bloowroom_training",schema = "profiledetails")
@Getter
@Setter
@Data
public class BloowRoomTraining
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "b_t_id", nullable = false)
    private Long b_t_id;
    private String balePluker;
    private String varioClean;
    private String unimix;
    private String supremo;
    private String vetalCombo;
    private String waste;
    private String collectionPaint;
    private String contamitionSorting;
    private String floorCleaning;
    private String remarks;


}
