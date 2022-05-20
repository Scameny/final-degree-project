import React, { useRef, useLayoutEffect, useEffect } from 'react';
import { useSelector } from 'react-redux';
import * as d3 from 'd3';
import * as legend from 'd3-svg-legend'
import * as d3hexbin from 'd3-hexbin';
import { BackLink } from '../../common';
import { FormattedMessage } from 'react-intl';

import * as selectors from '../selectors';
import Filtro from './Filtro';


const RedHexagonal = () => {


  const cuentas = useSelector(selectors.getCuentas);
  const d3Container = useRef(null);


  useLayoutEffect(() => {
    if (d3Container.current && cuentas.length > 0) {
      const domain = [-1.0, -0.9, -0.8, 0.8, 0.9, 1.0]
      const color = ["#FF2300FF", "#FF2300CC", "#FF230080", "#0E518D80", "#0E518DCC", "#0E518DFF"]
      var colorScale = d3.scaleLinear(domain, color)
      var margin = {
        top: 150,
        right: 20,
        bottom: 50,
        left: 100
      },
        width = 650,
        height = 450;


      d3.selectAll("hexagon").remove();

      var linear = d3.scaleLinear()
        .domain(domain)
        .range(color);

      d3.select(d3Container.current).append("g")
        .attr("class", "legendLinear")
        .attr("transform", "translate(20,20)");

      var legendLinear = legend.legendColor()
        .shapeWidth(30)
        .cells(domain)
        .title("Porcentaje de bot a humano (%)")
        .orient('horizontal')
        .scale(linear);

      d3.select(d3Container.current).select(".legendLinear")
        .call(legendLinear);



      //The number of columns and rows of the heatmap
      var MapColumns = Math.floor(Math.sqrt(cuentas.length)) + Math.floor(Math.sqrt(Math.floor(Math.sqrt(cuentas.length)))),
        MapRows = Math.floor(cuentas.length / MapColumns) + Math.ceil((cuentas.length - MapColumns * Math.floor(cuentas.length / MapColumns)) / MapColumns);


      //The maximum radius the hexagons can have to still fit the screen
      var hexRadius = d3.min([width / ((MapColumns + 0.5) * Math.sqrt(3)),
      height / ((MapRows + 1 / 3) * 1.5)]);

      //Restamos el ratio de aumento cuando pasamos el ratón por encima de uno de los hexágonos
      hexRadius -= hexRadius / 8;

      //Calculate the center position of each hexagon
      var points = [];
      for (var i = 0; i < MapColumns; i++) {
        for (var j = 0; j < MapRows; j++) {
          var x = hexRadius * i * Math.sqrt(3)
          //Offset each uneven row by half of a "hex-width" to the right
          if (j % 2 === 1) x += (hexRadius * Math.sqrt(3)) / 2
          var y = hexRadius * j * 1.5;
          if (j + 1 == MapRows && MapColumns * MapRows > cuentas.length) {
            if (j % 2 == 1 && cuentas.length % MapColumns > i) {
              points.push([x, y]);
            }
            else if (j % 2 == 1)
              continue
            else if (i < cuentas.length % MapColumns)
              points.push([x, y]);
            else
              continue;
          } else
            points.push([x, y]);
        }//for j
      }//for i






      //Create SVG element
      var svg = d3.select(d3Container.current)
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + (margin.top + hexRadius / 2) + ")");


      //Set the hexagon radius
      var hexbin = d3hexbin.hexbin().radius(hexRadius);
      //Draw the hexagons
      svg.append("g")
        .selectAll(".hexagon")
        .data(hexbin(points))
        .enter().append("path")
        .attr("class", "hexagon")
        .attr("d", d => "M" + d.x + "," + d.y + hexbin.hexagon())
        .attr("fill", function (d, i) {
          return colorScale(cuentas[i].porcentajeBot ? -cuentas[i].porcentajeBot : cuentas[i].porcentajeHumano)
        })
        .on("mouseover", function (d, i) {
          d3.select(".c-username")
            .classed("invisible", false)
            .select(".userName")
            .html("@" + cuentas[i].nombreUsuario);
          d3.select(".c-username")
            .select(".porcentaje")
            .html((cuentas[i].porcentajeBot ? cuentas[i].porcentajeBot * 100 : cuentas[i].porcentajeHumano * 100) + "%");
          d3.select(this)
            .attr("d", function (d) {
              this.parentNode.appendChild(this)
              return "M" + d.x + "," + d.y + hexbin.hexagon((hexRadius + hexRadius / 8));
            })
        })
        .on('mouseout', function (d) {
          d3.select(".c-username")
            .classed("invisible", true)
          d3.select(this)
            .attr("d", function (d) {
              return "M" + d.x + "," + d.y + hexbin.hexagon();
            })
        })
        .attr("stroke", "white")
        .attr("stroke-width", hexRadius / 20)



    }

  }, [cuentas]);







  return (
    <div>
      <BackLink />
      {!cuentas ? null :
        <div className="container">
          <div className="row">
            <svg className="col-" ref={d3Container}>
            </svg>
            <div className="col">
              <Filtro />
            </div>
            <div className="col-2m h-25">
              <div className="card row">
                <div className="card-body">
                  <FormattedMessage id='project.conjunto.fields.numeroCuentas' />: <b>{cuentas.length}</b>
                </div>
              </div>
              <br />
              <div className="invisible card row c-username">
                <div className="card-body">
                  <p className="userName"></p>
                  <FormattedMessage id='project.conjunto.fields.porcentaje' />: <b className="porcentaje"></b>
                </div>
              </div>
            </div>
          </div>

        </div>

      }
    </div>
  );


}


export default RedHexagonal;