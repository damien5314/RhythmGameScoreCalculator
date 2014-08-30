package com.ddiehl.rgsc;

public class CalculatorModule {
    private static final String TAG = CalculatorModule.class.getSimpleName();
    private String mName;
    private Class mClass;

    public CalculatorModule(String name, Class class_in) {
        setModuleName(name);
        setModuleClass(mClass);
    }

    public Class getModuleClass() {
        return mClass;
    }

    public void setModuleClass(Class mClass) {
        this.mClass = mClass;
    }

    public String getModuleName() {
        return mName;
    }

    public void setModuleName(String mName) {
        this.mName = mName;
    }
}
