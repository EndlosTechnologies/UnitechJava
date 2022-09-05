package com.unitechApi.store.po.view;

import com.unitechApi.store.indent.Model.IndentStatus;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PoByIndentView {
    private Long ind_id;
    private String indentnumber;
    private Float amount;
    private String ponumber;
    private String poname;
    private IndentStatus indentstatus;
    private String comment;
    private Long poid;
}
