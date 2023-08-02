package com.sluv.backoffice.domain.item.entity;

import com.sluv.backoffice.domain.brand.entity.Brand;
import com.sluv.backoffice.domain.brand.entity.NewBrand;
import com.sluv.backoffice.domain.celeb.entity.Celeb;
import com.sluv.backoffice.domain.celeb.entity.NewCeleb;
import com.sluv.backoffice.domain.item.enums.ItemStatus;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "item")
public class Item extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "celeb_id")
    private Celeb celeb;

    @ManyToOne
    @JoinColumn(name = "new_celeb_id")
    private NewCeleb newCeleb;

    @ManyToOne
    @JoinColumn(name = "item_category_id")
    @NotNull
    private ItemCategory category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "new_brand_id")
    private NewBrand newBrand;
    @NotNull
    @Size(max = 100)
    private String name;

    private LocalDateTime whenDiscovery;

    @Size(max = 100)
    private String whereDiscovery;

    @NotNull
    private int price;

    @Size(max = 45)
    private String color;

    @Column(columnDefinition = "TEXT")
    private String additionalInfo;

    @Column(columnDefinition = "TEXT")
    private String infoSource;

    @Column(name = "view_num")
    private Long viewNum;

    @Enumerated(EnumType.STRING)
    @Column(length = 45, columnDefinition = "varchar(45) default 'ACTIVE'")
    private ItemStatus itemStatus;
    public void increaseViewNum(){
        this.viewNum++;
    }
    public void decreaseViewNum(){
        // 레디스 적용전 임시
        this.viewNum--;
    }
    public void changeStatus(ItemStatus itemStatus){
        this.itemStatus = itemStatus;
    }
}
