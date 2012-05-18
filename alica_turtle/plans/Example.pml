<?xml version="1.0" encoding="ASCII"?>
<alica:Plan xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:alica="http:///de.uni_kassel.cn" id="1337239596521" name="Example" comment="" masterPlan="false" utilityFunction="" utilityThreshold="1.0" minCardinality="0" maxCardinality="2147483647">
  <states id="1337239596523" name="Begin" comment="" entryPoint="1337239596525">
    <plans xsi:type="alica:BehaviourConfiguration">Spawn.beh#1337242331627</plans>
    <outTransitions>#1337242346024</outTransitions>
  </states>
  <states id="1337242336944" name="Spawned" comment="">
    <plans xsi:type="alica:Plan">Position.pml#1337246194633</plans>
    <inTransitions>#1337242346024</inTransitions>
  </states>
  <transitions id="1337242346024" name="" comment="" msg="">
    <preCondition id="1337242354606" name="" comment="" conditionString="ownPos!=null" enabled="true"/>
    <inState>#1337239596523</inState>
    <outState>#1337242336944</outState>
  </transitions>
  <entryPoints id="1337239596525" name="" comment="" successRequired="true" minCardinality="0" maxCardinality="2147483647">
    <task>../Misc/taskrepository.tsk#1337239596602</task>
    <state>#1337239596523</state>
  </entryPoints>
</alica:Plan>
