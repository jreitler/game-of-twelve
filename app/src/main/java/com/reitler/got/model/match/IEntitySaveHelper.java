package com.reitler.got.model.match;

import com.reitler.got.model.data.entity.MatchEntity;
import com.reitler.got.model.data.entity.ScoreDataEntity;

public interface IEntitySaveHelper {

    void save(MatchEntity matchEntity);

    void save(ScoreDataEntity scoreDataEntity);
}
