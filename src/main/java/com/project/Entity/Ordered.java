package com.project.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ordered {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String orderNo;

    int totalOrderAmount;

    LocalDateTime orderDate;

    String cardUsed;

    @ManyToOne
    @JoinColumn
    Customer customer;

    @OneToMany(mappedBy = "ordered", cascade = CascadeType.ALL)
    List<Item> itemList = new ArrayList<>();
}
