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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public final class FindMeetingQuery {

  /*
  * This function is responsible for adding the "find a meeting" feature using the existing API.
  *  @param Collection<Event> events has a collection of the events happening
  *
  *
  *
  *
  *
  *
  **/
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    
    long meetingDuration = request.getDuration();      // This is the duration of the request meeting
    Collection<String> meetingAttendees = request.getAttendees();    // This is a collection of strings that have the names of all the attendees in the to be set meeting

    if ( (meetingDuration <= TimeRange.WHOLE_DAY.duration()) == false)
      return Arrays.asList();

    else if(events.isEmpty()) 
      return Arrays.asList(TimeRange.WHOLE_DAY);
    

    // This ArrayList contains all the unavailable times of TimeRanges at which the request meeting cannot be set. 
    // This is calculated or set by taking each event from the collection, and checking if the event attendees are busy in the collection
    // of meetings, and hence, can't be available for the meeting request.  
    ArrayList<TimeRange> unavailableTimes = new ArrayList<TimeRange>();

    for (Event event : events){
      Collection<String> eventAttendees = event.getAttendees();
      TimeRange rangeOfEvent = event.getWhen();
     
      for (String attendee : meetingAttendees) {    // checking the attendees of the meeting request 
        if (eventAttendees.contains(attendee)){
          unavailableTimes.add(rangeOfEvent);        // Comes here only if even one of the members of the request cannot make it    
          break;                                    // So immediately exits
        } 
      }
    }

    Collections.sort(unavailableTimes, TimeRange.ORDER_BY_START);

    // At this time, we have a list of timeRanges in unavailableTimes where the meeting cannot be set that's is sorted by order of start time. 
    // Now let us traverse the timeranges where the meeting can be set within the working hours. 

    Collection<TimeRange> availableTimes = new ArrayList<TimeRange>();  // This will have the times that have a collection of all the available timeranges of events

    int progress = TimeRange.START_OF_DAY;            // This variable behaves like an iterator throughout the day. So, it starts from 0, which is the START time.  
    
    for(TimeRange rangeOfEvent : unavailableTimes){
      int rangeEnd = rangeOfEvent.end();
      int rangeStart = rangeOfEvent.start();
        if (progress + meetingDuration <= rangeStart){       // If there can be a possible meeting with the requested meeting duration and that starts from progress, then it's valid.
          TimeRange availableRange = TimeRange.fromStartEnd(progress, rangeStart, false);
          availableTimes.add(availableRange);
        }
      
      progress = Math.max(rangeEnd,progress);       // Set progress to be after the end of either events
    }

    if (progress + meetingDuration <= 24 *60){
      TimeRange endOfDayMeeting = TimeRange.fromStartEnd(progress, 24 * 60 , false);
      availableTimes.add(endOfDayMeeting);
    }



    return availableTimes;

}
}