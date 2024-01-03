package si.fri.rso.komentar.models.converters;

import si.fri.rso.komentar.lib.Komentar;
import si.fri.rso.komentar.models.entities.KomentarEntity;

public class KomentarConverter {

    public static Komentar toDto(KomentarEntity entity) {

        Komentar dto = new Komentar();
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> parent of 410ac78 (init)
        dto.setId(entity.getId());
>>>>>>> parent of e9a4a0d (rollback)
        dto.setUstvarjen(entity.getUstvarjen());
        dto.setKomentar(entity.getKomentar());
        dto.setOcena(entity.getOcena());
        dto.setLokacija_id(entity.getLokacija_id());
        dto.setUser_id(entity.getUser_id());


        return dto;

    }

    public static KomentarEntity toEntity(Komentar dto) {

        KomentarEntity entity = new KomentarEntity();
        entity.setUstvarjen(dto.getUstvarjen());
        entity.setKomentar(dto.getKomentar());
        entity.setOcena(dto.getOcena());
        entity.setLokacija_id(dto.getLokacija_id());
        entity.setUser_id(dto.getUser_id());


        return entity;

    }

}
