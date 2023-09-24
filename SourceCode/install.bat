@echo OFF
jar cmvf MANIFEST.MF BrainShift.jar ./ .
java -jar BrainShift.jar