package me.fodded.apiservice.datatypes;

import com.google.gson.Gson;
import lombok.Getter;

@Getter
public abstract class AbstractData implements IDataResponse {

    protected static final Gson GSON = new Gson();
    private final String dataName;

    public AbstractData(String dataName) {
        this.dataName = dataName;
        DataRegistry.getInstance().registryData(this);
    }
}
