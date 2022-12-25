package cl.ey.desafio.api.user.mapper;

@FunctionalInterface
public interface Convert<T, R> {

	R from (T t);
}
