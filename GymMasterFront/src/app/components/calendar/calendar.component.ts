import { Component, inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CalendarOptions, DateSelectArg, EventApi, EventClickArg, EventInput } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import interactionPlugin from '@fullcalendar/interaction';
import { Router } from '@angular/router';
import { JwtService } from 'src/app/service/jwt.service';

interface GymClass {
  id: number;
  name: string;
  description?: string;
  startTime: string;
  endTime: string;
  maxCapacity: number;
  coachId: number;
  customerIds?: number[];
}

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent {
    
    private readonly router = inject(Router);
    public readonly service = inject(JwtService);
    calendarOptions: CalendarOptions = {
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
        },
        plugins: [ dayGridPlugin, interactionPlugin, timeGridPlugin, listPlugin ],
        initialView: 'dayGridMonth',
        weekends: true,
        editable: true,
        selectable: true,
        selectMirror: true,
        dayMaxEvents: true,
        /* you can update a remote database when these fire:
        eventAdd:
        eventChange:
        eventRemove:
        */
      };
      currentEvents: EventApi[] = [];
    
    constructor() {
        this.service.getClasses().subscribe((res) => {
            this.calendarOptions = {
                ... this.calendarOptions,
                events: res.map((tes) => ({ title: tes.name, start: tes.startTime})),
                select: this.handleDateSelect.bind(this),
                eventClick: this.handleEventClick.bind(this),
                eventsSet: this.handleEvents.bind(this),
            }
            console.log(this.calendarOptions.events);
            

        })
    }

      handleDateSelect(selectInfo: DateSelectArg) {
        if (this.service.isAdmin()) {
            console.log(111111111111);
            
            this.navigate();   
        }
      }
    
      handleEventClick(clickInfo: EventClickArg) {
        if (this.service.isAdmin()) {
            if (confirm(`Are you sure you want to delete the event '${clickInfo.event.title}'`)) {
                clickInfo.event.remove();
              } 
        }
        
      }
    
      handleEvents(events: EventApi[]) {
        this.currentEvents = events;
      }

      navigate() {
        this.router.navigate(['newClass']);
      }
}



