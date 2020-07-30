// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;

public final class FindMeetingQuery {
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        //throw new UnsupportedOperationException("TODO: Implement this method.");
        events.removeIf(event -> !existCommonAttendees(event.getAttendees(), request.getAttendees()));
    }

    /* 
    * Checks if there is exists an intersection(a common attendee) of the attendee list of two events
    */
    private boolean existCommonAttendees(Set<String> attendeesEventA, Collection<String> attendeesEventB) {
        //swap hashSets so that I can iterate through the shorter one
        /*if (attendeesEventA.size()>attendeesEventB.size()) { 
            Set<String> helper = attendeesEventA;
            attendeesEventA = attendeesEventB;
            attendeesEventB = helper;
        }*/

        //itarate through the attendees of event A and check if any of them also attends event B. If so,return true
        Iterator iteratorB = attendeesEventB.iterator(); 
        boolean foundCommonAttendee = false;
        while (iteratorB.hasNext() && !foundCommonAttendee) { 
            if (attendeesEventA.contains(iteratorB.next())) {
                foundCommonAttendee=true;
            }
        } 
        return foundCommonAttendee;
    }
}
