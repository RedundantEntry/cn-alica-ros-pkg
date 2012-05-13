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
set(LIBRARY_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/../../lib)

#set(DOC_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/doc)

#uncomment if you have defined messages
rosbuild_genmsg()
rosbuild_gen_cs_msg()
#uncomment if you have defined services
#rosbuild_gensrv()

#AutoDiff WorldModel Engine Expressions ConstraintSolver

FILE(GLOB AD_FILES src/AutoDiff/*.cs 
				src/AutoDiff/Compiled/*.cs
				src/AutoDiff/Properties/*.cs
	)

IF(NOT CMAKE_CSHARP_COMPILER MATCHES ".*dmcs")
	FILE(GLOB AD_FILES2 src/AutoDiff/Mono/*.cs)
	SET(AD_FILES "${AD_FILES} ${AD_FILES2}")		
ENDIF(NOT CMAKE_CSHARP_COMPILER MATCHES ".*dmcs")

CSHARP_ADD_LIBRARY(AutoDiff ${AD_FILES})

#FILE(GLOB_RECURSE AD_FILES src/AutoDiff/*.cs) 



#create Engine:
FILE(GLOB AE_FILES src/Engine/*.cs 
	src/Engine/Collections/*.cs 
	src/Engine/Model/*.cs 
	src/Engine/ConstraintModul/*.cs 
	src/Engine/Parser/*.cs 
	src/Engine/ExpressionHandler/*.cs 
	src/Engine/Rules/*.cs 
	src/Engine/UtilitySummands/*.cs 
	src/Engine/PlanSelector/*.cs 
	src/Engine/Logging/*.cs 
	src/Engine/AllocationAuthority/*.cs
	src/Engine/SyncModul/*.cs
	src/Engine/BehaviourPool/*.cs
	src/Engine/RoleAssignment/*.cs
	src/Engine/TeamObserver/*.cs
	src/Engine/Properties/*.cs
	)
	
CSHARP_ADD_TARGET_DEPENDENCY(AlicaEngine Castor AutoDiff Mono.C5)
CSHARP_ADD_LIBRARY(AlicaEngine ${AE_FILES})


#create CGSolver
FILE(GLOB CGS_FILES src/ConstraintSolver/*.cs)
CSHARP_ADD_TARGET_DEPENDENCY(CGSolver AlicaEngine AutoDiff Castor Mono.C5)
CSHARP_ADD_LIBRARY(CGSolver ${CGS_FILES})



#CSHARP_MakeDoc(AlicaEngine AutoDiff CGSolver)
#CSHARP_MakeDoc(AlicaEngine TeamObserver AutoDiff RoleAssignment BehaviourPool SyncModul CGSolver)

