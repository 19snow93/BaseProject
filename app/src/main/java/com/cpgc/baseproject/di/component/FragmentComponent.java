package com.cpgc.baseproject.di.component;

import com.cpgc.baseproject.base.di.scope.FragmentScope;
import com.cpgc.baseproject.di.module.FragmentModule;

import dagger.Component;

/**
 * Created by leo on 2017/5/16.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
}
