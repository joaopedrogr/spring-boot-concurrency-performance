package com.example.springbootconcurrencyperformance.controller;


import com.example.springbootconcurrencyperformance.threads.FixedThreadExample;
import com.example.springbootconcurrencyperformance.threads.ManualThreadExample;
import com.example.springbootconcurrencyperformance.threads.ScheduledThreadExample;
import com.example.springbootconcurrencyperformance.threads.VirtualThreadsExample;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController( "/threads")
public class ThreadController {

    private final ManualThreadExample manualThreadExample;
    private final FixedThreadExample fixedThreadExample;
    private final ScheduledThreadExample scheduledThreadExample;
    private final VirtualThreadsExample virtualThreadsExample;

    public ThreadController(ManualThreadExample manualThreadExample, FixedThreadExample fixedThreadExample,
                            ScheduledThreadExample scheduledThreadExample, VirtualThreadsExample virtualThreadsExample) {
        this.manualThreadExample = manualThreadExample;
        this.fixedThreadExample = fixedThreadExample;
        this.scheduledThreadExample = scheduledThreadExample;
        this.virtualThreadsExample = virtualThreadsExample;
    }

    @PostMapping("/manual")
    public void executeManualThread() {
        this.manualThreadExample.execute();
    }

    @PostMapping("/fixed")
    public void executeFixedThread() {
        this.fixedThreadExample.execute();
    }

    @PostMapping("/schedule")
    public void executeScheduledThread() throws InterruptedException {
        this.scheduledThreadExample.execute();
    }

    @PostMapping("/virtual")
    public void executeVirtualThread() throws InterruptedException {
        this.virtualThreadsExample.execute();
    }
}
