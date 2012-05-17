<?xml version="1.0" encoding="ASCII"?>
<alica:Plan xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:alica="http:///de.uni_kassel.cn" id="1337246194633" name="Position" comment="" masterPlan="false" utilityFunction="" utilityThreshold="1.0" minCardinality="0" maxCardinality="2147483647">
  <conditions xsi:type="alica:RuntimeCondition" id="1337246237969" name="NewRuntimeCondition" comment="This is a runtime condition. It must hold while this plan is executed. This specifc runtime condition constraints the goal positions of all turtles executing this plan." conditionString="True">
    <quantifiers xsi:type="alica:ForallAgents" id="1337246249013" name="" comment="" scope="1337246194633">
      <sorts>gposx</sorts>
      <sorts>gposy</sorts>
    </quantifiers>
  </conditions>
  <states id="1337246213063" name="Position" comment="" entryPoint="1337246213064">
    <plans xsi:type="alica:BehaviourConfiguration">GoToPos.beh#1337245634486</plans>
  </states>
  <entryPoints id="1337246213064" name="" comment="" successRequired="true" minCardinality="0" maxCardinality="2147483647">
    <task>../Misc/taskrepository.tsk#1337239596602</task>
    <state>#1337246213063</state>
  </entryPoints>
</alica:Plan>
