package com.intellij.xml.util;

import com.intellij.html.impl.RelaxedHtmlFromSchemaNSDescriptor;
import com.intellij.psi.filters.*;
import com.intellij.psi.filters.position.NamespaceFilter;
import com.intellij.psi.filters.position.TargetNamespaceFilter;
import com.intellij.psi.meta.MetaDataContributor;
import com.intellij.psi.meta.MetaDataRegistrar;
import com.intellij.psi.xml.*;
import com.intellij.xml.impl.schema.NamedObjectDescriptor;
import com.intellij.xml.impl.schema.SchemaNSDescriptor;
import com.intellij.xml.impl.schema.XmlAttributeDescriptorImpl;
import com.intellij.xml.impl.schema.XmlElementDescriptorImpl;

/**
 * @author Maxim.Mossienko
 */
public class XmlApplicationComponent implements MetaDataContributor {
  public void contributeMetaData(final MetaDataRegistrar registrar) {
    {
      registrar.registerMetaData(
          new AndFilter(
              new NamespaceFilter(XmlUtil.SCHEMA_URIS),
              new ClassFilter(XmlDocument.class)
          ),
          SchemaNSDescriptor.class
      );

      registrar.registerMetaData(
          new AndFilter(XmlTagFilter.INSTANCE, new NamespaceFilter(XmlUtil.SCHEMA_URIS), new XmlTextFilter("schema")),
          SchemaNSDescriptor.class
      );
    }
    {
      registrar.registerMetaData(
          new OrFilter(
              new AndFilter(
                  new ContentFilter(
                    new OrFilter(
                      new ClassFilter(XmlElementDecl.class),
                      new ClassFilter(XmlConditionalSection.class),
                      new ClassFilter(XmlEntityRef.class)
                    )
                  ),
                  new ClassFilter(XmlDocument.class)
              ),
              new ClassFilter(XmlMarkupDecl.class)
          ),
          com.intellij.xml.impl.dtd.XmlNSDescriptorImpl.class
      );
    }

    {
      registrar.registerMetaData(new AndFilter(XmlTagFilter.INSTANCE, new NamespaceFilter(XmlUtil.SCHEMA_URIS), new XmlTextFilter("element")),
                         XmlElementDescriptorImpl.class);
    }

    {
      registrar.registerMetaData(
          new AndFilter(XmlTagFilter.INSTANCE, new NamespaceFilter(XmlUtil.SCHEMA_URIS), new XmlTextFilter("attribute")),
          XmlAttributeDescriptorImpl.class
      );
    }

    {
      registrar.registerMetaData(
          new ClassFilter(XmlElementDecl.class),
          com.intellij.xml.impl.dtd.XmlElementDescriptorImpl.class
      );
    }

    {
      registrar.registerMetaData(
          new ClassFilter(XmlAttributeDecl.class),
          com.intellij.xml.impl.dtd.XmlAttributeDescriptorImpl.class
      );
    }

    {
      registrar.registerMetaData(
          new AndFilter(
              new ClassFilter(XmlDocument.class),
              new TargetNamespaceFilter(XmlUtil.XHTML_URI),
              new NamespaceFilter(XmlUtil.SCHEMA_URIS)),
          RelaxedHtmlFromSchemaNSDescriptor.class
      );
    }

    {
      registrar.registerMetaData(new AndFilter(XmlTagFilter.INSTANCE, new NamespaceFilter(XmlUtil.SCHEMA_URIS), new XmlTextFilter("complexType",
                                                                                                                                  "simpleType", "group",
                                                                                                                                  "attributeGroup")),
                         NamedObjectDescriptor.class);
    }
  }
}
