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
                List<Node> target;
                String target_name;
                switch (cmd_parse.length) {
                    case 1:
                    case 2:
                        target = root.links;
                        target_name = repl.current_table;
                        break;
                    default:
                        target = parse_select(Arrays.copyOfRange(cmd_parse, 2, cmd_parse.length), repl);
                        target_name = "";
                }
                System.out.println("\t" + target_name);
                for (Node node : target) {
                    System.out.printf("%s\t%d\t%s\n", node.toString(), node.index, node.data);
                }
                break;
            default:
                System.out.println("Could not parse : " + cmd_parse.toString());
                break;
        }
    }

    static Node table_shallow_copy(Node src) {
        Node dest = new Node(src.data);
        for (Node node : src.links) dest.links.add(node);
        return dest;
    }

    static List<Node> parse_select(String[] cmd, Repl repl) {
        Map<String, String> cfg = parse_select(cmd, new HashMap<>());
        System.out.println(cfg.toString());

        assert (!cfg.containsKey("from")) : "missing 'from' clause";
        Node output = table_shallow_copy(repl.tables.get(cfg.get("from")));

        if (cfg.containsKey("inner_join")) {
            Node to_join = table_shallow_copy(repl.tables.get(cfg.get("inner_join")));
            Collections.sort(output.links);
            Collections.sort(to_join.links);

            Iterator<Node> it = output.links.iterator(), it2 = to_join.links.iterator();
            Node cur = it.next(), cur2 = it2.next();
            while (true) {
                int cmp = cur.compareTo(cur2);
                while (cmp > 0 && it2.hasNext()) {
                    cur2 = it2.next();
                    cmp = cur.compareTo(cur2);
                }
                if (cmp < 0) {
                    it.remove(); // no match i.e. cmp == 0
                }

                if (!(it.hasNext() && it2.hasNext())) { //exit and cleanup
                    if (!it2.hasNext()) {
                        if (cmp > 0)
                            it.remove();
                        while (it.hasNext()) {
                            it.next();
                            it.remove();
                        }
                    }
                    break;
                }
                cur = it.next();
            }
        } else if (cfg.containsKey("outer_join")) {
            System.out.println("Not implemented");
        } else {
        }
        return output.links;
    }

    static Map<String, String> parse_select(String[] cmd, Map<String, String> cfg) {
        for (int cur = 0; cmd.length - cur > 1; cur += 2)
            cfg.putIfAbsent(cmd[cur], cmd[cur + 1]);
        return cfg;
    }
}


class Node implements Comparable<Node> {
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

    public int compareTo(Node d) {
        return this.index - d.index;
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

