package com.sluv.backoffice.domain.item.entity;

import com.sluv.backoffice.domain.item.enums.ItemEditReqReason;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "item_edit_req")
public class ItemEditReq extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_edit_req_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    @NotNull
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @NotNull
    private Item item;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ItemEditReqReason itemEditReqReason;

    @Size(max = 1002)
    private String content;

}
