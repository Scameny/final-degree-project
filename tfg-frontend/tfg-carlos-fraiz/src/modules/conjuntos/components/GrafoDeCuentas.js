import * as d3 from "d3";
import { useSelector } from 'react-redux';
import React, { useRef, useLayoutEffect } from 'react';
import * as selectors from '../selectors';
import { BackLink } from '../../common';
import { FormattedMessage } from 'react-intl';
import * as legend from 'd3-svg-legend'






const GrafoDeCuentas = () => {

  const cuentas = useSelector(selectors.getCuentas);
  const interacciones = useSelector(selectors.getInteracciones);
  const d3Container = useRef(null);






  useLayoutEffect(() => {
    if (d3Container.current && interacciones.length > 0) {


      function dragstarted(d) {
        if (!d3.event.active) simulation.alphaTarget(0.01).restart();//sets the current target alpha to the specified number in the range [0,1].
        d.fy = d.y; //fx - the node’s fixed x-position. Original is null.
        d.fx = d.x; //fy - the node’s fixed y-position. Original is null.
      }

      //When the drag gesture starts, the targeted node is fixed to the pointer
      function dragged(d) {
        d.fx = d3.event.x;
        d.fy = d3.event.y;
      }

      var cuenta = null;
      var nodes = []
      const set = new Set();


      const domain = [-1.0, -0.9, -0.8, 0.8, 0.9, 1.0]
      const colorNodos = ["#FF2300FF", "#FF2300CC", "#FF230080", "#0E518D80", "#0E518DCC", "#0E518DFF"]
      var colorScale = d3.scaleLinear(domain, colorNodos)

      interacciones.forEach(function (i) {
        if (!set.has(i.source)) {
          set.add(i.source);
          cuenta = cuentas.find(x => x.nombreUsuario === i.source);
          nodes.push({ id: i.source, quantity: 1, resultado: cuenta.porcentajeBot ? -cuenta.porcentajeBot : cuenta.porcentajeHumano })
        }
        nodes.find(obj => obj.id === i.source).quantity += 1


        if (!set.has(i.target)) {
          set.add(i.target);
          cuenta = cuentas.find(x => x.nombreUsuario === i.source)
          nodes.push({ id: i.target, quantity: 1, resultado: cuenta.porcentajeBot ? -cuenta.porcentajeBot : cuenta.porcentajeHumano })
        }
        nodes.find(obj => obj.id === i.target).quantity += 1
      });


      d3.select(".nodos")
        .html(nodes.length)

      d3.select(".interacciones")
        .html(interacciones.length)

      var margin = {
        top: 30,
        right: 80,
        bottom: 5,
        left: 5
      }

      var width = 1000,
        height = 650;


      const color = d3.scaleOrdinal(["MeGusta", "follow", "Retweet", "Undefined"], ["#F73B13", "#00acee", "#23DC17", "#DCDCDC"])


      const simulation = d3.forceSimulation(nodes)
        .force("center", d3.forceCenter(width / 2, height / 2))
        .force("link", d3.forceLink(interacciones).id(d => d.id).distance(120))
        .force("charge", d3.forceManyBody().strength(-200))
        .force("x", d3.forceX().strength(0.05))
        .force("y", d3.forceY().strength(0.05));

      const svg = d3.select(d3Container.current)
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .style("font", "12px sans-serif")
        .append("g")
        .attr("transform", `translate(${margin.left},${margin.top})`)
        .call(d3.zoom()
          .scaleExtent([.5, 8])
          .on("zoom", zoom))
        .on("dblclick.zoom", null)
        .append("svg:g")
        .attr("transform", "translate(100,50) scale(0.5,0.5)");

      svg.append("rect")
        .attr("fill", "none")
        .attr("pointer-events", "all")
        .attr("width", "100%")
        .attr("height", "100%")


      const types = Array.from(new Set(interacciones.map(d => d.interacción)))



      const link = svg.append("g")
        .attr("fill", "none")
        .attr("stroke-width", 1.5)
        .selectAll("path")
        .data(interacciones)
        .join("path")
        .classed("links", true)
        .attr("stroke", d => color(d.interacción))
        // eslint-disable-next-line no-restricted-globals
        .attr("marker-end", d => `url(${new URL(`#arrow-${d.interacción}`, location)})`);

      svg.append("defs").selectAll("marker")
        .data(types)
        .join("marker")
        .attr("id", d => `arrow-${d}`)
        .attr("viewBox", "0 -5 10 10")
        .attr("refX", 30)
        .attr("refY", -2.5)
        .attr("markerWidth", 6)
        .attr("markerHeight", 6)
        .attr("orient", "auto")
        .append("path")
        .attr("fill", color)
        .attr("d", "M0,-5L10,0L0,5");

      const edgepaths = svg.selectAll(".edgepath") //make path go along with the link provide position for link labels
        .data(interacciones)
        .enter()
        .append('path')
        .attr('class', 'edgepath')
        .attr('fill-opacity', 1)
        .attr('stroke-opacity', 0)
        .attr('id', function (d, i) { return 'edgepath' + i })
        .style("pointer-events", "none");

      const edgelabels = svg.selectAll(".edgelabel")
        .data(interacciones)
        .enter()
        .append('text')
        .style("pointer-events", "none")
        .attr('class', 'edgelabel')
        .attr('dy', d => (d.interacción === "MeGusta") ? -30 : -5)
        .attr('id', function (d, i) { return 'edgelabel' + i })

      const text = edgelabels.append('textPath') //To render text along the shape of a <path>, enclose the text in a <textPath> element that has an href attribute with a reference to the <path> element.
        .attr('xlink:href', function (d, i) { return '#edgepath' + i })
        .style("text-anchor", "middle")
        .style("pointer-events", "none")
        .attr("startOffset", "50%");


      const selectionSimulation = d3.forceSimulation()
        .force("center", d3.forceCenter(width / 2, height / 2))
        .force("link", d3.forceLink().id(d => d.id).distance(10))
        .force("charge", d3.forceManyBody().strength(200))

      const node = svg.selectAll(".nodes")
        .data(nodes)
        .enter()
        .append("g")
        .attr("class", "nodes")
        .call(d3.drag() //sets the event listener for the specified typenames and returns the drag behavior.
          .on("start", dragstarted) //start - after a new pointer becomes active (on mousedown or touchstart).
          .on("drag", dragged)      //drag - after an active pointer moves (on mousemove or touchmove).
          //.on("end", dragended)     //end - after an active pointer becomes inactive (on mouseup, touchend or touchcancel).
        ).on("dblclick", function (c) {
          const nodes = new Set();
          const links = new Set();
          d3.selectAll(".links")
            .attr("stroke", function (l) {
              if (l.source.id != c.id && l.target.id != c.id) {
                // eslint-disable-next-line no-restricted-globals
                d3.select(this).attr("marker-end", `url(${new URL(`#arrow-Undefined`, location)})`);
                return "#DCDCDC80";
              }
              else {
                nodes.add(l.source.id)
                nodes.add(l.target.id)
                links.add(l)
                // eslint-disable-next-line no-restricted-globals
                d3.select(this).attr("marker-end", `url(${new URL(`#arrow-${l.interacción}`, location)})`);
                return color(l.interacción)
              }
            })
          d3.selectAll(".ndc")
            .attr("fill", function (d) {
              if (nodes.has(d.id))
                return colorScale(d.resultado)
              else
                return "#DCDCDC80"
            });

          simulation.stop()
         

            
          d3.select(".c-username")
            .classed("invisible", false)
            .select(".userName")
            .html("@" + c.id);
          d3.select(".c-username")
            .select(".porcentaje")
            .html(c.resultado * 100 + "%");


          text.text(function (d, i) {
            if (d.source.id == c.id || d.target.id == c.id)
              return d.quantity
          }).attr("fill", d => color(d.interacción));

        });


      let threshold = 5
      let threshold_ratio = 20

      node.append("circle")
        .attr("r", d => Math.min(Math.max(d.quantity * 2, threshold), threshold_ratio))//+ d.runtime/20 )
        .classed("ndc", true)
        .attr("fill", d => colorScale(d.resultado))
        .style("stroke", "grey")
        .style("stroke-opacity", 0.3)
        .style("stroke-width", d => d.quantity / 2)
        .attr("cursor", "pointer");



      node.append("text")
        .attr("dy", 4)
        .attr("dx", -15)
        .text(d => d.id);

      simulation
        .on("tick", ticked);


      function zoom() {
        d3.selectAll('svg g').filter(function () {
          let f = this.firstChild
          if (f == undefined) return true
          let r = (f.tagName == 'rect' && !(f.className.baseVal == "swatch"))
          return r
        }).attr("transform", d3.event.transform);
      }
      function linkArc(d) {
        const l = (d.interacción === "MeGusta") ? 30 : 0
        const r = Math.hypot(d.target.x - d.source.x, d.target.y - d.source.y);
        return `
          M${d.source.x},${d.source.y}
          A${r},${r - l} 0 0,1 ${d.target.x},${d.target.y}
        `;
      }

      function ticked() {
        link.attr("d", linkArc);

        node.attr("transform", d => `translate(${d.x},${d.y})`);


        edgepaths.attr('d', d => 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y);
      }

      const legend_g = d3.select("#d3legend").selectAll(".legend")
        .data(types)
        .enter().append("g")
        .attr("transform", (d, i) => `translate(${40},${(i + 1) * 20})`);

      legend_g.append("circle")
        .attr("cx", 0)
        .attr("cy", 0)
        .attr("r", 5)
        .attr("fill", color);


      legend_g.append("text")
        .attr("x", 10)
        .attr("y", 5)
        .text(d => d);

      d3.select("#d3legend").append("g")
        .attr("class", "legendLinear")
        .attr("transform", `translate(${20},${90})`);

      var linear = d3.scaleLinear()
        .domain(domain)
        .range(colorNodos);

      var legendLinear = legend.legendColor()
        .shapeWidth(30)
        .cells(domain)
        .title("Porcentaje de bot a humano (%)")
        .orient('horizontal')
        .scale(linear);

      d3.select("#d3legend").select(".legendLinear")
        .call(legendLinear);




    }
  }, [interacciones, cuentas]);




  return (
    <div>
      <BackLink />
      {(!interacciones || !cuentas) ? null :
        <div className="container">
          <div className="row">
            <svg className="col border border-dark px-0" ref={d3Container}></svg>
            <div className="col-1m h-25">
              <div className="card row w-100 ml-2">
                <div className="card-body">
                  <p><FormattedMessage id='project.conjunto.fields.numeroCuentas' />: <b>{cuentas.length}</b></p>
                  <p><FormattedMessage id='project.conjunto.fields.nodos' />: <b className="nodos"></b></p>
                  <p><FormattedMessage id='project.conjunto.fields.interacciones' />: <b className="interacciones"></b></p>
                </div>
              </div>
              <br />
              <div className="invisible card row w-100 ml-2 c-username">
                <div className="card-body">
                  <p className="userName"></p>
                  <FormattedMessage id='project.conjunto.fields.porcentaje' />: <b className="porcentaje"></b>
                </div>
              </div>
              <br />
              <svg className="row w-100 ml-2 h-100" id="d3legend"></svg>
            </div>
          </div>

        </div>}
    </div>
  );
}

export default GrafoDeCuentas;