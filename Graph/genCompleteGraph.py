import networkx as nx

def genCompleteGraph(n):
    f = open("clique_data\complete_graph_"+str(n)+".txt", "w")
    g = nx.complete_graph(n)
    for i in g.edges():
        f.write(str(i[0])+" "+str(i[1])+"\n")
    f.close()

def main():
    for n in xrange(100, 10000, 100):
        print n
        genCompleteGraph(n)

if __name__ == '__main__':
    main()