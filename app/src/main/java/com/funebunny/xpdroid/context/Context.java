package com.funebunny.xpdroid.context;

import com.funebunny.xpdroid.gastos.backend.service.IServicioGastosDAO;

/**
 * Created by schmidt0 on 6/27/2015.
 */
//@Singleton
//@Component
public interface Context {
    IServicioGastosDAO servicioGastos();

}
