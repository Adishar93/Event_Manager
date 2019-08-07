package com.asaproject.eventnotifier;

import android.support.annotation.NonNull;

import java.util.Date;

public class KeyForEvents implements Comparable
{
    EventItems ei;
    String key;
    public KeyForEvents(EventItems ei,String key)
    {
     this.ei=ei;
     this.key=key;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        KeyForEvents kwe=(KeyForEvents)o;
        if(new Date(this.ei.getDateOfEvent()).compareTo(new Date(kwe.ei.getDateOfEvent()))==1)
            return 1;
        else if(new Date(this.ei.getDateOfEvent()).compareTo(new Date(kwe.ei.getDateOfEvent()))==-1)
            return -1;
        else
            return 0;
    }
}
