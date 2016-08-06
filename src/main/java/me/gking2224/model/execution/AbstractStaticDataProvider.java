package me.gking2224.model.execution;

public abstract class AbstractStaticDataProvider implements StaticDataProvider {
    
    @Override
    public String getShortIdentifier() {
        return getIdentifier().substring((getIdentifier().lastIndexOf('.')+1));
    }

}
