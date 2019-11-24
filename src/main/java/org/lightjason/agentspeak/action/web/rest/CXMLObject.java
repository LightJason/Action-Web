/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason                                                #
 * # Copyright (c) 2015-19, LightJason (info@lightjason.org)                            #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package org.lightjason.agentspeak.action.web.rest;

import org.lightjason.agentspeak.common.IPath;
import org.lightjason.agentspeak.error.context.CExecutionIllegealArgumentException;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.execution.IContext;
import org.lightjason.agentspeak.language.fuzzy.IFuzzyValue;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


/**
 * action to call a restful webservice with XML data.
 * Creates a literal of an XML REST-webservice, first argument
 * is the URL of the webservice and second argument the functor of the literal
 *
 * {@code W = web/rest/xmlobject( "https://en.wikipedia.org/wiki/Special:Export/AgentSpeak", "wikipedia" );}
 *
 * @see <a href="https://en.wikipedia.org/wiki/Representational_state_transfer"></a>
 * @see <a href="https://en.wikipedia.org/wiki/Web_service"></a>
 * @see <a href="https://en.wikipedia.org/wiki/XML"></a>
 */
public final class CXMLObject extends IBaseRest
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 8156192343303737293L;
    /**
     * action name
     */
    private static final IPath NAME = namebyclass( CXMLObject.class, "web", "rest" );

    @Nonnull
    @Override
    public IPath name()
    {
        return NAME;
    }

    @Nonnull
    @Override
    public Stream<IFuzzyValue<?>> execute( final boolean p_parallel, @Nonnull final IContext p_context,
                                           @Nonnull final List<ITerm> p_argument, @Nonnull final List<ITerm> p_return
    )
    {
        try
        {
            final Map<String, ?> l_data = IBaseRest.xml( p_argument.get( 0 ).raw() );
            p_return.add(
                p_argument.size() == 2
                ? CLiteral.of( p_argument.get( p_argument.size() - 1 ).<String>raw(), flatterm( l_data ) )
                : IBaseRest.baseliteral(
                    p_argument.stream().skip( 1 ).map( ITerm::raw ),
                    flatterm( l_data )
                )
            );

            return Stream.empty();
        }
        catch ( final IOException l_exception )
        {
            throw new CExecutionIllegealArgumentException( p_context, l_exception );
        }
    }
}
