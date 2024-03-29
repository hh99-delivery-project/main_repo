package com.example.delivery.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 20)
    private String workTime;

    @Column(nullable = false, length = 25)
    private String category;

    @Column(nullable = false, length = 100)
    private String address;

    private String imageUrl;

    private float storeScore;

    private double totalSales;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>();

    @Builder
    public Store(Integer id, String name, String workTime, String category, String address, String imageUrl, float storeScore, double totalSales, User user) {
        this.id = id;
        this.name = name;
        this.workTime = workTime;
        this.category = category;
        this.address = address;
        this.imageUrl = imageUrl;
        this.storeScore = storeScore;
        this.totalSales = totalSales;
        this.user = user;
    }

    // ID를 제외한 나머지 필드를 초기화하는 생성자
    public Store(String name, String workTime, String category, String address, String imageUrl, float storeScore, double totalSales, User user) {
        this.name = name;
        this.workTime = workTime;
        this.category = category;
        this.address = address;
        this.imageUrl = imageUrl;
        this.storeScore = storeScore;
        this.totalSales = totalSales;
        this.user = user;
    }

    // 주문 완료 > 총 주문 금액을 매출에 더해주는 메서드
    public void plusSales(double orderTotalPrice) {
        this.totalSales += orderTotalPrice;
    }
}
