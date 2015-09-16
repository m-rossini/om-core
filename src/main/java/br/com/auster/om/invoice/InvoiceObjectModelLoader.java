/*
 * Copyright (c) 2004 Auster Solutions. All Rights Reserved.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Created on Feb 3, 2005
 */
package br.com.auster.om.invoice;

import java.util.List;

import org.w3c.dom.Element;

/**
 * <p><b>Title:</b> InvoiceObjectModelLoader</p>
 * <p><b>Description:</b> An interface for object model loaders</p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: InvoiceObjectModelLoader.java 111 2006-01-31 16:52:48Z etirelli $
 */
public interface InvoiceObjectModelLoader {
  public void configure(Element config) throws Exception;
  public List getObjects();
  public void cleanup();
}
