package br.com.compass.search.enums;

public enum GenresEnum {
    ACAO(28L), AVENTURA(12L), ANIMACAO(16L),
    COMEDIA(35L), CRIME(80L), DOCUMENTARIO(99L),
    DRAMA(18L), FAMILIA(10751L), FANTASIA(14L),
    HISTORIA(36L), TERROR(27L), MUSICA(10402L),
    MISTERIO(9648L), ROMANCE(10749L), FICCAO_CIENTIFICA(878L),
    CINEMA_TV(10770L), THIILLER(53L), GUERRA(10752L),
    FAROESTE(37L);

    private Long idGenrer;

    GenresEnum(Long idGenrer) {
        this.idGenrer = idGenrer;
    }

    public Long getIdGenrer() {
        return this.idGenrer;
    }

    public static GenresEnum valueOfId(Long idGenrer) {
        for (GenresEnum genresEnum : values()) {
            if (genresEnum.idGenrer == idGenrer) {
                return genresEnum;
            }
        }
        return null;
    }
}
