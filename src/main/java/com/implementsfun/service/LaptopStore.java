package com.implementsfun.service;
import com.implementsfun.protoj.FilterMessage;
import com.implementsfun.protoj.LaptopMessage.*;
import io.grpc.Context;

import javax.management.openmbean.KeyAlreadyExistsException;

public interface LaptopStore {
    void save(Laptop laptop)throws KeyAlreadyExistsException;
    Laptop find(String id);
    void search(Context ctx, FilterMessage.Filter filter,LaptopStream stream);
}
