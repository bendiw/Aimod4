from py4j.java_gateway import JavaGateway

g = JavaGateway()                        # connect to the JVM
pc = g.entry_point.getProblemCreator()
print(pc)
p = pc.create("C:\\Users\\Bendik\\Documents\\GitHub\\Aimod4\\TSP\\1.txt", 0)
print(p.getCoords().get(0)[0])
#g.jvm.java.lang.System.out.println(p.getCoords) # call a static method
