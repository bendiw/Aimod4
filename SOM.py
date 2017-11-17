from py4j.java_gateway import JavaGateway

g = JavaGateway()                        # connect to the JVM
som = g.entry_point.getSOMtrainer()
i =0

def tsp(case, nodes_x=250, iterations=2000000, initRad=20.0, learningRate=0.2, printInterval=100000):
    global i
    if( i>0):
        hide()
    som.init(case, 0, 200, nodes_x, 1, iterations, initRad, learningRate, printInterval, 10, 10);
    som.run()
    i+=1

def mnist(mbs=1, numNodesX=28, numNodesY=28, iterations=40000, initRad=28.0, learningRate=0.3, printInterval=2000, trainCases=10000, testCases=100):
    global i
    if(i>0):
        hide()
    som.init("8", 1, mbs, numNodesX,numNodesY, iterations, initRad, learningRate, printInterval, trainCases,  testCases);
    som.run()
    i+=1

def renew():
    som = g.entry_point.getSOMtrainer()
    
def hide():
    som.hideCards()



#g.jvm.java.lang.System.out.println(p.getCoords) # call a static method
