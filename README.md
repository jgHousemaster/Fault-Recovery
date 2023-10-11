# Fault-Recovery

## Introduction

This is a simulation of Fault Recovery tactic (Active Redundancy) written in Java. Designed and Created by team 3 for SWEN 755.

There is a Fault Monitor and 2 Nodes. The Nodes simulate programs that read data from a file and operate some calculation. Because it cannot control the data source, there might be random crash.

The Fault Monitor uses Heartbeat tactic to detect the fault.

## To run the program
Use a Java IDE. Run **src/FaultMonitor** first, then run **src/ActiveNode**, lastly run **src/RedundantNode**. Wrong order wouldn't work.

Observe the console of the **FaultMonitor** which shows the heartbeat process and Fault Recovery clearly. When the Active Node crashes, the receiver would pop up a warning window, then switch to redundant node.
