package com.mahhaus.zeronota.api.nfce.mapper;

import lombok.Data;

/**
 * @author josias.soares
 * Create 19/09/2019
 */
@Data
public class NFCeRequest {
    private String chNFe; //: 53170711759085000102650010000000011000000045
    private String nVersao; //: 100
    private Long tpAmb; //: 1
    private String cDest; //: 86346741187
    private String dhEmi; //: 323031372D30372D30375431353A34343A30302D30333A3030
    private Double vNF; //: 50.00
    private Double vICMS; //: 0.00
    private String digVal; //: 675643374B595566695958375556636C46554D34336F56342F376F3D
    private String cIdToken; //:
    private String cHashQRCode; //: 358e3bc508ea11032be96e97cd92d4228aea05d0
}
