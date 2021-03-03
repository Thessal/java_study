import java.util.Scanner;
import java.util.*;

public class HelloWorld1536 {
    public static void main(String[] args) {
        Repl repl = new Repl();
    }
}

class Repl {
    String current_table = "default";
    Map<String, Node> tables = new HashMap<>();
    Boolean exit = false;
    String str;
    Scanner sc = new Scanner(System.in);

    Repl() {
        tables.put(current_table, new Node(0, current_table));

        while (true) {
            System.out.print(current_table + " > ");
            str = sc.nextLine();
            Parser.parse(tables.get(current_table), str, this);
            if (exit) {
                break;
            }
        }
    }
}

class Parser {
    public static void parse(Node root, String cmd, Repl repl) {
        String[] cmd_parse = cmd.split(" ");
        if (cmd_parse.length == 0) {
            return;
        }
        switch (cmd_parse[0]) {
            case "exit":
                repl.exit = true;
                break;
            case "table":
                if (cmd_parse.length == 2) {
                    repl.current_table = cmd_parse[1];
                    repl.tables.putIfAbsent(repl.current_table, new Node(0, repl.current_table));
                }
                break;
            case "tables":
                System.out.println(repl.tables.keySet());
                break;
            case "insert":
                switch (cmd_parse.length) {
                    case 2:
                        root.links.add(new Node(root.index, cmd_parse[1]));
                        root.index += 1;
                        break;
                    case 3:
                        root.links.add(new Node(Integer.parseInt(cmd_parse[1]), cmd_parse[2]));
                        break;
                }
                break;
            case "select":
                System.out.println("\t"+repl.current_table);
                for (Node node : root.links) {
                    System.out.printf("%s\t%d\t%s\n", node.toString(), node.index, node.data);
                }
                break;
            default:
                System.out.println("Could not parse : " + cmd_parse.toString());
                break;
        }
    }

    static Node[] parse_join() {
        return (new Node[0]);
    }
}


class Node {
    List<Node> links = new ArrayList<>();
    int index;
    String data;

    Node(int index, String data) {
        this.index = index;
        this.data = data;
    }

    Node(String data) {
        this.index = -1;
        this.data = data;
    }
}

class GraphAdjList extends Node {
    GraphAdjList() {
        super("");
    }
}

class GraphAdjMatrix extends Node {
    GraphAdjMatrix() {
        super("");
    }
}

