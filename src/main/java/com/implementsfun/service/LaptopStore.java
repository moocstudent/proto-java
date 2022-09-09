package com.implementsfun.service;
import com.implementsfun.protoj.LaptopMessage.*;

import javax.management.openmbean.KeyAlreadyExistsException;

public interface LaptopStore {
    void save(Laptop laptop)throws KeyAlreadyExistsException;
    Laptop find(String id);
}
