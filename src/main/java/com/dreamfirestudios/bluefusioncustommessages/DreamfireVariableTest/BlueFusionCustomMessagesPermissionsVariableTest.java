package com.dreamfirestudios.bluefusioncustommessages.DreamfireVariableTest;

import com.dreamfirestudios.dreamCore.DreamfirePersistentData.PersistentDataTypes;
import com.dreamfirestudios.dreamCore.DreamfireVariable.DreamfireVariableTest;
import com.dreamfirestudios.bluefusioncustommessages.Enum.Permissions;

import java.util.ArrayList;
import java.util.List;

public class BlueFusionCustomMessagesPermissionsVariableTest implements DreamfireVariableTest {
    @Override
    public PersistentDataTypes PersistentDataType() { return PersistentDataTypes.STRING; }

    @Override
    public boolean IsType(Object variable) {
        try{
            var test = Permissions.valueOf(variable.toString());
            return true;
        }catch (IllegalArgumentException ignored){ return false; }
    }

    @Override
    public List<Class<?>> ClassTypes() {
        var data = new ArrayList<Class<?>>();
        data.add(Permissions.class);
        data.add(Permissions[].class);
        return data;
    }

    @Override
    public Object SerializeData(Object serializedData) {
        return serializedData.toString();
    }

    @Override
    public Object DeSerializeData(Object serializedData) {
        return Permissions.valueOf(serializedData.toString());
    }

    @Override
    public Object ReturnDefaultValue() { return Permissions.EnableSystem; }

    @Override
    public List<String> TabData(List<String> list, String s) {
        var data = new ArrayList<String>();
        for(var x : Permissions.values()) if(x.name().contains(s)) data.add(x.name());
        return data;
    }
}
