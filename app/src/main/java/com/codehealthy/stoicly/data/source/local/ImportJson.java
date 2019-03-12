package com.codehealthy.stoicly.data.source.local;

import java.util.List;

interface ImportJson<T> {

    List<T> getListFrom(String jsonName);
}
