package top.sillyfan.cassandra.support;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.CollectionFactory;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.*;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mapping.model.ConvertingPropertyAccessor;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update;

/**
 *
 * 对于td来说，Entity的属性值为null则不更新。
 *
 * 但是Spring Data Cassandra 1.5版本开始，属性为null则更新cassandra字段为null。 所以重写
 * {@link org.springframework.data.cassandra.core.convert.MappingCassandraConverter}
 * 生成cql的时候，忽略null值的字段生成.
 *
 * 代码都从Spring Data Cassandra项目
 * {@link org.springframework.data.cassandra.core.convert.MappingCassandraConverter}
 * 拷贝而来；
 *
 * TODO: 源码拷贝，导致后续升级存在问题。
 *
 * TODO: 需要有更好的实现方法，比如通过注解或者WriteOptions的方法来控制，而不是拷贝代码。
 *
 * <p>
 * Null values are skipped in inserts/updates prior to Spring Data Cassandra
 * 1.5.
 * <p>
 * https://jira.spring.io/browse/DATACASS-182
 * <p>
 * https://github.com/spring-projects/spring-data-cassandra/pull/72 <br/>
 *
 */
public class NullIgnoredMappingCassandraConverter extends MappingCassandraConverter {

    private static Logger log = LoggerFactory.getLogger(NullIgnoredMappingCassandraConverter.class);

    public NullIgnoredMappingCassandraConverter(CassandraMappingContext context) {
        super(context);
    }

    protected void writeUpdateFromWrapper(final ConvertingPropertyAccessor accessor, final Update update,
                                          final CassandraPersistentEntity<?> entity) {

        for (CassandraPersistentProperty property : entity) {

            Object value = getWriteValue(property, accessor);

            if (property.isCompositePrimaryKey()) {

                CassandraPersistentEntity<?> compositePrimaryKey = super.getMappingContext().getRequiredPersistentEntity(property);

                writeUpdateFromWrapper(getConvertingAccessor(value, compositePrimaryKey), update, compositePrimaryKey);
                continue;
            }

            if(value != null) {
                if (isPrimaryKeyPart(property)) {
                    update.where(QueryBuilder.eq(property.getColumnName().toCql(), value));
                } else {
                    // 如果value为空，就不进行更新
                    update.with(QueryBuilder.set(property.getColumnName().toCql(), value));
                }
            }
        }
    }

    /**
     * Retrieve the value to write for the given {@link CassandraPersistentProperty} from
     * {@link ConvertingPropertyAccessor} and perform optionally a conversion of collection element types.
     *
     * @param property the property.
     * @param accessor the property accessor
     * @return the return value, may be {@literal null}.
     */
    private <T> T getWriteValue(CassandraPersistentProperty property, ConvertingPropertyAccessor accessor) {
        return (T) getWriteValue(accessor.getProperty(property, (Class<T>) getTargetType(property)),
                property.getTypeInformation());
    }

    private Object getWriteValue(Object value, TypeInformation<?> typeInformation) {

        if (value == null) {
            return null;
        }

        Class<?> requestedTargetType = typeInformation != null ? typeInformation.getType() : Object.class;

        if (getCustomConversions().hasCustomWriteTarget(value.getClass(), requestedTargetType)) {
            return getConversionService().convert(value, getCustomConversions()
                    .getCustomWriteTarget(value.getClass(), requestedTargetType).orElse(requestedTargetType));
        }

        if (getCustomConversions().hasCustomWriteTarget(value.getClass())) {
            return getConversionService().convert(value, getCustomConversions().getCustomWriteTarget(value.getClass()).get());
        }

        if (getCustomConversions().isSimpleType(value.getClass())) {
            return getPotentiallyConvertedSimpleValue(value, requestedTargetType);
        }

        TypeInformation<?> type = typeInformation != null ? typeInformation
                : ClassTypeInformation.from((Class) value.getClass());

        TypeInformation<?> actualType = type.getActualType();

        if (value instanceof Collection) {

            Collection<Object> original = (Collection<Object>) value;
            Collection<Object> converted = CollectionFactory.createCollection(getCollectionType(type), original.size());

            for (Object element : original) {
                converted.add(convertToColumnType(element, actualType));
            }

            return converted;
        }

        BasicCassandraPersistentEntity<?> entity = getMappingContext().getPersistentEntity(actualType.getType());

        if (entity != null && entity.isUserDefinedType()) {

            UDTValue udtValue = entity.getUserType().newValue();

            write(value, udtValue, entity);

            return udtValue;
        }

        return value;
    }

    /**
     * Performs special enum handling or simply returns the value as is.
     *
     * @param value may be {@literal null}.
     * @param requestedTargetType must not be {@literal null}.
     * @see CassandraType
     */
    @SuppressWarnings("unchecked")
    private Object getPotentiallyConvertedSimpleValue(Object value, Class<?> requestedTargetType) {

        if (value == null) {
            return null;
        }

        // Cassandra has no default enum handling - convert it to either a String
        // or, if requested, to a different type
        if (Enum.class.isAssignableFrom(value.getClass())) {
            if (requestedTargetType != null && !requestedTargetType.isEnum()
                    && getConversionService().canConvert(value.getClass(), requestedTargetType)) {

                return getConversionService().convert(value, requestedTargetType);
            }

            return ((Enum<?>) value).name();
        }

        return value;
    }

    /**
     * Returns whether the property is part of the primary key.
     *
     * @param property {@link CassandraPersistentProperty} to evaluate.
     * @return a boolean value indicating whether the given property is party of a primary key.
     */
    private boolean isPrimaryKeyPart(CassandraPersistentProperty property) {
        return (property.isCompositePrimaryKey() || property.isPrimaryKeyColumn() || property.isIdProperty());
    }

    private Class<?> getTargetType(CassandraPersistentProperty property) {

        return getCustomConversions().getCustomWriteTarget(property.getType()).orElseGet(() -> {

            if (property.isAnnotationPresent(CassandraType.class)) {
                return getPropertyTargetType(property);
            }

            if (property.isCompositePrimaryKey() || getCustomConversions().isSimpleType(property.getType())
                    || property.isCollectionLike()) {

                return property.getType();
            }

            return getPropertyTargetType(property);
        });

    }

    private Class<?> getPropertyTargetType(CassandraPersistentProperty property) {

        DataType dataType = getMappingContext().getDataType(property);

        if (dataType instanceof UserType) {
            return property.getType();
        }

        TypeCodec<Object> codec = CodecRegistry.DEFAULT_INSTANCE.codecFor(getMappingContext().getDataType(property));

        return codec.getJavaType().getRawType();
    }

    private static Class<?> getCollectionType(TypeInformation<?> type) {

        if (type.getType().isInterface()) {
            return type.getType();
        }

        if (ClassTypeInformation.LIST.isAssignableFrom(type)) {
            return ClassTypeInformation.LIST.getType();
        }

        if (ClassTypeInformation.SET.isAssignableFrom(type)) {
            return ClassTypeInformation.SET.getType();
        }

        if (!type.isCollectionLike()) {
            return ClassTypeInformation.LIST.getType();
        }

        return type.getType();
    }


    /**
     * Create a new {@link ConvertingPropertyAccessor} for the given source and entity.
     *
     * @param source must not be {@literal null}.
     * @param entity must not be {@literal null}.
     * @return a new {@link ConvertingPropertyAccessor} for the given source and entity.
     */
    private ConvertingPropertyAccessor getConvertingAccessor(Object source, CassandraPersistentEntity<?> entity) {

        PersistentPropertyAccessor propertyAccessor = (source instanceof PersistentPropertyAccessor
                ? (PersistentPropertyAccessor) source
                : entity.getPropertyAccessor(source));

        return new ConvertingPropertyAccessor(propertyAccessor, getConversionService());
    }
}
