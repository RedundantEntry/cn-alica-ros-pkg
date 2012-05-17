cmake_minimum_required(VERSION 2.4.6)
include($ENV{ROS_ROOT}/core/rosbuild/rosbuild.cmake)

# Set the build type.  Options are:
#  Coverage       : w/ debug symbols, w/o optimization, w/ code-coverage
#  Debug          : w/ debug symbols, w/o optimization
#  Release        : w/o debug symbols, w/ optimization
#  RelWithDebInfo : w/ debug symbols, w/ optimization
#  MinSizeRel     : w/o debug symbols, w/ optimization, stripped binaries
#set(ROS_BUILD_TYPE RelWithDebInfo)

rosbuild_init()
rosbuild_include(roscs cncs)

#set the default path for built executables to the "bin" directory
set(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/bin)
#set the default path for built libraries to the "lib" directory
set(LIBRARY_OUTPUT_PATH $ENV{ES_ROOT}/lib)


rosbuild_genmsg()
rosbuild_gen_cs_msg()

rosbuild_gen_cs_srv()


FILE(GLOB WM_FILES src/WorldModel/*.cs)

CSHARP_ADD_TARGET_DEPENDENCY(TurtleWorldModel AlicaEngine Castor)
CSHARP_ADD_LIBRARY(TurtleWorldModel ${WM_FILES})

FILE(GLOB_RECURSE EXP_FILES src/Expressions/*.cs)
CSHARP_ADD_TARGET_DEPENDENCY(TurtleExpressions AlicaEngine AutoDiff Castor TurtleWorldModel)
CSHARP_ADD_LIBRARY(TurtleExpressions ${EXP_FILES})

FILE(GLOB BEH_FILES src/Behaviours/*.cs)
CSHARP_ADD_TARGET_DEPENDENCY(TurtleBehaviours AlicaEngine Castor TurtleWorldModel)
CSHARP_ADD_LIBRARY(TurtleBehaviours ${BEH_FILES})


CSHARP_ADD_TARGET_DEPENDENCY(AlicaTurtle AlicaEngine Castor TurtleWorldModel)
CSHARP_ADD_EXE(AlicaTurtle src/AlicaTurtle.cs)