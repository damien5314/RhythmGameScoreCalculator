package com.ddiehl.rgsc.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;

import com.ddiehl.rgsc.R;

public class ModuleListTest extends ActivityInstrumentationTestCase2<ModuleList> {
    private static final String TAG = ModuleListTest.class.getSimpleName();
    private static final int NUM_MODULES = 5;
    private ModuleList moduleList;

    public ModuleListTest() { super(ModuleList.class); } /* Default constructor */

    @Override
    public void setUp() throws Exception {
        super.setUp();
        moduleList = getActivity();
    }

    public void testContentsOfModuleList() throws Exception {
        ListView vModuleList = (ListView) moduleList.findViewById(R.id.module_list);
        assertEquals(NUM_MODULES, vModuleList.getChildCount());
        for (int i = 0; i < vModuleList.getChildCount(); i++) {
            View vModule = vModuleList.getChildAt(i);
        }
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
