package br.com.compass.search.enums;

import java.util.Objects;

public enum ProvidersEnum {
    NETFLIX(8L), AMAZON_PRIME_VIDEO(119L), AMAZON_PRIME(9L), APPLE_ITUNES(2L),
    GOOGLE_PLAY_MOVIES(3L), SUN_NXT(309L), MUBI(11L), LOOKE(47L), CLASSIX(445L), STAR_PLUS(619L),
    PARAMOUNT_PLUS(531L), HBO_MAX(384L), ARGO(534L), EVENTIVE(677L), CULTPIX(692L), CLARO_VIDEO(167L),
    TELECINE_PLAY(227L), GLOBOPLAY(307L), APPLE_TV_PLUS(350L), AMAZON_VIDEO(10L), FILMBOX(701L),
    VIX(457L), CURIOSITY_STREAM(190L), SPAMFLIX(521L), FUNIMATION_NOW(269L), DOCSVILLE(475L), NETMOVIES(19L),
    STARZ_PLAY_AMAZON_CHANNEL(194L), WOW_PRESENTS_PLUS(546L), MAGELLAN_TV(551L), PARAMOUNT_AMAZON_CHANNEL(582L),
    BROADWAYHD(554L), BELAS_ARTES_A_LA_CARTE(447L), FILMZIE(559L), DEKKO(444L), BELIEVE(465L), TRUE_STORY(567L),
    HBO_GO(31L), DOCALLIANCE_FILMS(569L), HOICHOI(315L), KOREAONDEMAND(575L), OLDFLIX(499L), PLUTO_TV(300L),
    TNTGO(512L), GOSPEL_PLAY(477L), DISNEY_PLUS(337L), NOW(484L), LIBREFLIX(544L), SUPO_MUNGAM_PLUS(530L), FILME_FILME(566L),
    STARZ(43L), KINOPOP(573L), OI_PLAY(574L), MGM_AMAZON_CHANNEL(588L), MICROSOFT_STORE(68L), LOOKE_AMAZON_CHANNEL(683L),
    REVRY(473L), MOVIESAINTS(562L);

    private Long idProvider;

    ProvidersEnum(Long idProvider) {
        this.idProvider = idProvider;
    }

    public Long getIdProvider() {
        return idProvider;
    }

    public static ProvidersEnum valueOfId(Long idProvider) {
        for (ProvidersEnum providersEnum : values()) {
            if (Objects.equals(providersEnum.idProvider, idProvider)) {
                return providersEnum;
            }
        }
        return null;
    }
}
