package com.github.belousovea.sockwarehouse.spec;

import com.github.belousovea.sockwarehouse.model.entity.Socks;
import org.springframework.data.jpa.domain.Specification;

public class SocksSpec {

    public static Specification<Socks> hasColor(String color) {
        return (root, query, cb) -> color != null ? cb.equal(root.get("color"), color) : cb.conjunction();
    }

    public static Specification<Socks> hasCottonContent(int cottonContent, String Operator) {
        return (root, query, cb) -> switch (Operator) {
            case "moreThan" -> cb.greaterThan(root.get("cottonContent"), cottonContent);
            case "lessThan" -> cb.lessThan(root.get("cottonContent"), cottonContent);
            case "equal" -> cb.equal(root.get("cottonContent"), cottonContent);
            default -> cb.conjunction();
        };
    }

    public static Specification<Socks> hasCottonContentRange(int min, int max) {
        return (root, query, cb) -> cb.between(root.get("cottonContent"), min, max);

    }

    public static Specification<Socks> sortByColor() {
        return (root, query, cb) -> { query.orderBy(cb.asc(root.get("color")));
        return cb.conjunction();
        };
    }

    public static Specification<Socks> sortByCottonContent() {
        return (root, query, cb) -> { query.orderBy(cb.asc(root.get("cottonContent")));
        return cb.conjunction();
        };
    }
}
